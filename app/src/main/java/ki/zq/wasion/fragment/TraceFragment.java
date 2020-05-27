package ki.zq.wasion.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import ki.zq.wasion.R;
import ki.zq.wasion.activity.ScanCodeActivity;
import ki.zq.wasion.bean.Constants;
import ki.zq.wasion.bean.FindBean;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TraceFragment extends Fragment {

    private String TAG = "TAG";

    private EditText et_zcCode;
    private TextView tv_result;
    private Dialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ImageView igv_scan = view.findViewById(R.id.imageView_scan);
        et_zcCode = view.findViewById(R.id.editText_zcCode); //编号输入
        Button bt_find = view.findViewById(R.id.button_find); //搜索按键
        tv_result = view.findViewById(R.id.textView_result); //搜索结果显示

        bt_find.setOnClickListener(v -> {
            if (!et_zcCode.getText().toString().equals("")) {
                if (isPropertyNumber(et_zcCode.getText().toString())) {
                    new Thread(runnable).start();
                    loading = LoadingView.showDialog(requireContext());
                } else {
                    Toast.makeText(requireContext(), "编号格式错误，请检查！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "initViews: " + et_zcCode.getText().toString());
                Toast.makeText(requireContext(), "请手动或扫描输入编号！", Toast.LENGTH_SHORT).show();
            }
        });
        igv_scan.setOnClickListener(v -> new IntentIntegrator(requireActivity()).setCaptureActivity(ScanCodeActivity.class).initiateScan());
    }

    private FindBean getProductInfo(String propertyNumber) {
        OkHttpClient httpClient = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse("http://www.wasionit.com/WeiXin_Plum/sale/Index").newBuilder();
        builder.addQueryParameter("MeterNo", propertyNumber);
        // 创建post方式请求对象
        RequestBody body = new FormBody.Builder().add("Accept", "application/json, text/plain, */*")
                .add("Accept-Language", "zh-CN,zh;q=0.9")
                .add("Connection", "Keep-Alive")
                .add("Host", "www.wasionit.com")
                .add("Referer", "http://www.wasionit.com/WeiXin_Plum/sale/Index")
                .add("User-Agent", Constants.UserAgent)
                .add("Origin", "http://www.wasionit.com")
                // httpPost.setHeader("Content-Length", "16");
                .add("Accept-Encoding", "gzip, deflate").build();

        Request request = new Request.Builder().url(builder.build()).post(body).build();
        // 执行请求操作，并拿到结果（同步阻塞）
        String result = "";
        try {
            Response response = httpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null)
                result = responseBody.string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FindBean findBean = new FindBean();
        if (result.contains("NAME2")) {
            JSONObject object = JSONObject.parseObject(result.replace("null", "\"无\""));
            JSONArray array = object.getJSONArray("items");
            JSONObject endObject = array.getJSONObject(0);

            String[] getFromKey = new String[19];
            int i = 0;
            for (String key : endObject.keySet()) {
                getFromKey[i] = endObject.getString(key);
                i++;
            }

            findBean.setMES("MES合同：" + getFromKey[14]);
            findBean.setSoftwareVersion("软件版本：" + getFromKey[13]);
            findBean.setHardwareVersion("硬件版本：" + getFromKey[8]);
            findBean.setProductType("产品型号：" + getFromKey[5]);
            findBean.setVoltage("电压：" + getFromKey[15]);
            findBean.setSpecification("规格：" + getFromKey[17]);
            findBean.setCurrent("电流：" + getFromKey[16]);
            findBean.setSaleOder("销售订单：" + getFromKey[2] + "/" + getFromKey[18]);
            findBean.setCustomerManager("客户经理：" + getFromKey[7]);
            findBean.setCustomerUnit("客户单位：" + getFromKey[9]);
            findBean.setQuantity("数量：" + getFromKey[3]);
            findBean.setDeliveryTime("发货时间：" + getFromKey[12]);
            findBean.setSerialNumberRange("序列号范围：" + getFromKey[11]);
            findBean.setPropertyRange("资产号范围：" + getFromKey[0] + " - " + getFromKey[0]);
            findBean.setSAP("SAP批次号：" + getFromKey[1]);
            findBean.setProduceNumber("生产订单号：" + getFromKey[6]);
            findBean.setLongText("长文本：" + getFromKey[10]);
            return findBean;
        } else {
            findBean.setGetNothing("返回数据无效，请再次查询或检查资产/出厂编号是否有误！");
            return findBean;
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            FindBean bean = (FindBean) msg.getData().getSerializable("result");
            if (bean != null) {
                loading.dismiss();
                tv_result.setText(bean.getAll().replace("null", ""));
            }
            return true;
        }
    });

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Bundle bundle = new Bundle();
            FindBean bean;

            if (getProductInfo(et_zcCode.getText().toString()) != null) {
                bean = getProductInfo(et_zcCode.getText().toString());
                bundle.putSerializable("result", bean);
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
    };

    private boolean isPropertyNumber(String string) {
        String regex_normal = "^[a-z0-9A-Z]+$";

        boolean flag_nor = string.matches(regex_normal);
        boolean flag_num;

        int count = 0;
        for (int i = string.length(); --i >= 0; ) {
            if (Character.isDigit(string.charAt(i))) {
                count++;
            }
        }
        flag_num = count == string.length();

        Log.d(TAG, "isPropertyNumber: " + flag_nor + flag_num);

        if (flag_nor) {
            return true;
        } else return flag_num;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(requireContext(), "扫描结果为空！", Toast.LENGTH_SHORT).show();
            } else {
                if (et_zcCode.getText().toString().equals(""))
                    et_zcCode.setText(result.getContents());
                else {
                    et_zcCode.setText("");
                    et_zcCode.setText(result.getContents());
                }
            }
        }
    }
}

