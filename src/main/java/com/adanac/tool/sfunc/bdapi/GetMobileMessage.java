package com.adanac.tool.sfunc.bdapi;
import com.alibaba.fastjson.JSONObject;
/**
 * 
* @ClassName: GetMobileMessage
* @Description: TODO
* @author chenkaideng@star-net.cn
* @date 2016年1月28日 下午2:40:56
*
 */
public class GetMobileMessage{
    private static final String PHONE_PLACE_API_URL="http://virtual.paipai.com/extinfo/GetMobileProductInfo";
    /**
     * 
    * @Title: getMobilePlace 
    * @Description: 获取手机归属地信息
    * @param @param mobile
    * @param @return     
    * @return String     
    * @throws
     */
    public String getMobilePlace(String mobile){
        HttpClientUtil util = new HttpClientUtil();
        String[] strings={"",""};
        try {
            //访问拍拍的查询接口
            String mobileMessage = util.getWebcontent(PHONE_PLACE_API_URL+"?mobile="+mobile+"&amount=10000", "GB2312");
            strings = mobileMessage.split(";");
            //（页面获取到的消息，除了这些，还有一些html语句）
//            string[0]="({mobile:'15850781443',province:'江苏',isp:'中国移动',stock:'1',amount:'10000',maxprice:'0',minprice:'0',cityname:'南京'})";
            mobileMessage = strings[0];
            JSONObject jsonObject = JSONObject.parseObject(mobileMessage.substring(1, mobileMessage.length()-1));
            //解析出省份和city和运营商
            String province = jsonObject.getString("province");
            String cityname = jsonObject.getString("cityname");
            String isp = jsonObject.getString("isp");
            return isp+"&nbsp"+province+cityname;
        } catch (Exception e) {
            e.printStackTrace();
//            logger.error(strings[0]+e.toString());
            return "";
        } 
    }
}
