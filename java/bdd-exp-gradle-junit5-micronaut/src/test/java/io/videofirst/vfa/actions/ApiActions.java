package io.videofirst.vfa.actions;

import io.micronaut.http.HttpResponse;
import io.videofirst.vfa.Action;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
public class ApiActions {

    public ApiBuilder url(String url, String... params) {
        return new ApiBuilder(url); // FIXME do something with params
    }

    /**
     * Builder class.
     */
    public class ApiBuilder {

        private String url;
        private String body;
        private Map<String, Object> headers;

        public ApiBuilder(String url) {
            this.url = url;
        }

        public ApiBuilder header(String name, Object value) {
            headers.put(name, value);
            return this;
        }

        public ApiBuilder auth_basic(String username, Object password) {
            String authorization = "Basic " + username + ":" + password;
            headers.put("Authorization", authorization);
            return this;
        }

        public ApiBuilder body(String body) {
            this.body = body;
            return this;
        }

        @Action
        public HttpResponse get() {
            return HttpResponse.accepted();
        }

        @Action
        public HttpResponse post() {
            return HttpResponse.accepted();
        }

        @Action
        public HttpResponse put() {
            return HttpResponse.accepted();
        }

        @Action
        public HttpResponse delete() {
            return HttpResponse.accepted();
        }

    }

}