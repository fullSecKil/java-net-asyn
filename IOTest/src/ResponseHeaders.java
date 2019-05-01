/**
 * @file: ResponseHeaders.class
 * @author: Dusk
 * @since: 2019/4/25 0:21
 * @desc:
 */
public class ResponseHeaders {
    private int code;

    private String phrase;

    private String contentType;

    private int contentLength;

    private String server;

    public ResponseHeaders(int code, String phrase, String contentType, int contentLength, String server) {
        this.code = code;
        this.phrase = phrase;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.server = server;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "ResponseHeaders{" +
                "code=" + code +
                ", phrase='" + phrase + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentLength=" + contentLength +
                ", server='" + server + '\'' +
                '}';
    }
}
