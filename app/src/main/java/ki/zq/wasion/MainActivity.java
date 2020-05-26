package ki.zq.wasion;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import ki.zq.wasion.fragment.AboutFragment;
import ki.zq.wasion.fragment.ApplyFragment;
import ki.zq.wasion.fragment.EntryFragment;
import ki.zq.wasion.fragment.TraceFragment;

public class MainActivity extends AppCompatActivity {

    private String[] tabTitles = {"工单申请", "客户录入", "产品追溯", "关于"};
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ApplyFragment());
        fragmentList.add(new EntryFragment());
        fragmentList.add(new TraceFragment());
        fragmentList.add(new AboutFragment());
    }

    private void initViews() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        for (int i = 0; i < tabTitles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setTag(i);
            tab.setText(tabTitles[i]);
            tabLayout.addTab(tab);
        }
        tabLayout.setTabTextColors(Color.BLACK, Color.WHITE);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();
    }
}
