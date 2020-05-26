package ki.zq.wasion.bean;

import java.io.Serializable;

public class FindBean implements Serializable {
    private String MES;
    private String softwareVersion;
    private String hardwareVersion;
    private String productType;
    private String voltage;
    private String specification;
    private String current;
    private String saleOder;
    private String customerManager;
    private String customerUnit;
    private String quantity;
    private String deliveryTime;
    private String serialNumberRange;
    private String propertyRange;
    private String SAP;
    private String produceNumber;
    private String longText;
    private boolean success;
    private String getNothing;

    public String getAll() {
        String[] result = {
                MES, softwareVersion, hardwareVersion ,productType ,
                voltage,specification,current,saleOder ,customerManager,
                customerUnit,quantity ,deliveryTime,serialNumberRange,
                propertyRange ,SAP ,produceNumber,longText ,getNothing
        };
        StringBuilder builder = new StringBuilder();
        for (String string : result){
            if (string==null){
                builder.append("");
            }else {
                builder.append(string).append("\r\n");
            }
        }
        return builder.toString();

//        return MES + "\r\n"
//                + softwareVersion + "\r\n"
//                + hardwareVersion + "\r\n"
//                + productType + "\r\n"
//                + voltage + "\r\n"
//                + specification + "\r\n"
//                + current + "\r\n"
//                + saleOder + "\r\n"
//                + customerManager + "\r\n"
//                + customerUnit + "\r\n"
//                + quantity + "\r\n"
//                + deliveryTime + "\r\n"
//                + serialNumberRange + "\r\n"
//                + propertyRange + "\r\n"
//                + SAP + "\r\n"
//                + produceNumber + "\r\n"
//                + longText + "\r\n"
//                + getNothing;
    }

    public String getMES() {
        return MES;
    }

    public void setMES(String MES) {
        this.MES = MES;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getSaleOder() {
        return saleOder;
    }

    public void setSaleOder(String saleOder) {
        this.saleOder = saleOder;
    }

    public String getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(String customerManager) {
        this.customerManager = customerManager;
    }

    public String getCustomerUnit() {
        return customerUnit;
    }

    public void setCustomerUnit(String customerUnit) {
        this.customerUnit = customerUnit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSerialNumberRange() {
        return serialNumberRange;
    }

    public void setSerialNumberRange(String serialNumberRange) {
        this.serialNumberRange = serialNumberRange;
    }

    public String getPropertyRange() {
        return propertyRange;
    }

    public void setPropertyRange(String propertyRange) {
        this.propertyRange = propertyRange;
    }

    public String getSAP() {
        return SAP;
    }

    public void setSAP(String SAP) {
        this.SAP = SAP;
    }

    public String getProduceNumber() {
        return produceNumber;
    }

    public void setProduceNumber(String produceNumber) {
        this.produceNumber = produceNumber;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGetNothing() {
        return getNothing;
    }

    public void setGetNothing(String getNothing) {
        this.getNothing = getNothing;
    }
}
