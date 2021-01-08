package cn.deliver.water.wxpay.sdk;




import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class MyWxPayConfig extends WXPayConfig {


    private byte[] certData;


    public MyWxPayConfig() throws Exception {
        //String certPath = "D:\\WorkSpace\\YL\\deliverwater\\src\\main\\resources\\static\\apiclient_cert.p12";//从微信商户平台下载的安全证书存放的目录
		String certPath = "/home/apiclient/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String payCallback() {
        return "https://www.jinghexinxi.top/other/payCallback";
    }

    public String getAppSecret() {
        return "1411a43f4ced18646ed9526fd4d5b019";
    }

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    public String getAppID() {
        return "wxb0d149f1f9ad4e1b";
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    @Override
    public String getMchID() {
        return "1603116943";
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    public String getKey() {
        return "Jinanlvzhiyuan186601112201861861";
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    @Override
    public boolean shouldAutoReport() {
        return super.shouldAutoReport();
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    @Override
    public int getReportWorkerNum() {
        return super.getReportWorkerNum();
    }

    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    @Override
    public int getReportQueueMaxSize() {
        return super.getReportQueueMaxSize();
    }

    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    @Override
    public int getReportBatchSize() {
        return super.getReportBatchSize();
    }


}
