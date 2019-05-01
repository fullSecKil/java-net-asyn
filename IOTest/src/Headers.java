import java.util.HashMap;
import java.util.Map;

/**
 * @file: Headers.class
 * @author: Dusk
 * @since: 2019/4/24 23:11
 * @desc:
 */
public class Headers {
    private String method;

    private String path;

    private String version;

    private Map<String, String> headerMap = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(String key, String value) {
        this.headerMap.put(key, value);
    }

    @Override
    public String toString() {
        return "Headers{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", headerMap=" + headerMap +
                '}';
    }
}
