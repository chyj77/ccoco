package cn.ccoco.platform.monitor.endpoint;

import cn.ccoco.platform.common.annotation.CcocoEndPoint;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;

/**
 * @author MrBird
 */
@CcocoEndPoint
public class CcocoHttpTraceEndpoint {

    private final HttpTraceRepository repository;

    public CcocoHttpTraceEndpoint(HttpTraceRepository repository) {
        this.repository = repository;
    }

    public CcocoHttpTraceDescriptor traces() {
        return new CcocoHttpTraceDescriptor(this.repository.findAll());
    }

    public static final class CcocoHttpTraceDescriptor {

        private final List<HttpTrace> traces;

        private CcocoHttpTraceDescriptor(List<HttpTrace> traces) {
            this.traces = traces;
        }

        public List<HttpTrace> getTraces() {
            return this.traces;
        }
    }
}
