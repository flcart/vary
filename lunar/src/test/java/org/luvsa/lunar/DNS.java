package org.luvsa.lunar;

import com.aliyun.tea.TeaException;

/**
 * @author Aglet
 * @create 2023/2/13 14:36
 */
class DNS {


    public static com.aliyun.alidns20150109.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        var config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "alidns.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.alidns20150109.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        var args = java.util.Arrays.asList(args_);
        // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        var client = DNS.createClient("LTAI5tA7Z3EsdibzaEGX8Soj", "s5elRjxOh1Hg1Qmhv5AbEQUAHajQkE");
        var request = new com.aliyun.alidns20150109.models.AddCustomLineRequest()
                .setLang("en")
                .setDomainName("luvsa.cn")
                .setLineName("default");
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.addCustomLineWithOptions(request, new com.aliyun.teautil.models.RuntimeOptions());
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}
