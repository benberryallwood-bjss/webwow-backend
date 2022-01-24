package webwow.adapters.web.filters;

import com.vtence.molecule.Application;
import com.vtence.molecule.Middleware;

public class AllowCrossOrigin implements Middleware {

    String origin;

    public AllowCrossOrigin(String origin) {
        this.origin = origin;
    }

    public AllowCrossOrigin() {
        this("*");
    }

    @Override
    public Application then(Application next) {
        return request -> next.handle(request)
                .whenSuccessful(resp -> resp.addHeader("Access-Control-Allow-Origin", origin));
    }

}
