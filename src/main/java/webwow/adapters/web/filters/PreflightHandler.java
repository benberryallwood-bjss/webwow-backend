package webwow.adapters.web.filters;

import com.vtence.molecule.Application;
import com.vtence.molecule.Middleware;
import com.vtence.molecule.Response;
import com.vtence.molecule.http.HttpMethod;

public class PreflightHandler implements Middleware {

    @Override
    public Application then(Application next) {
        return request -> {
            if (request.method() == HttpMethod.OPTIONS) {
                return Response.ok().addHeader("Access-Control-Allow-Methods", "POST, PUT, DELETE")
                        .addHeader("Access-Control-Allow-Headers", "content-type").done();
            } else {
                return next.handle(request);
            }
        };
    }

}
