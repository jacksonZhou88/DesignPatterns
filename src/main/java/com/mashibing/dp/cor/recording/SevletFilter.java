package com.mashibing.dp.cor.recording;

import java.util.LinkedList;
import java.util.List;

public class SevletFilter {

    static class Request {

        public void req(String name) {
            System.out.println(name + " is requesting");
        }

    }

    static class Response {
        public void res(String name) {
            System.out.println(name + " is responsing");
        }
    }

    interface Filter {
        void doFilter(Request request, Response response, FilterChain chail);
    }

    static class SmileFilter implements Filter {

        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            request.req("SmileFilter");
            chain.doFilter(request, response, chain);
            response.res("SmileFilter");
        }
    }

    static class CryFilter implements Filter {

        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            request.req("CryFilter ");
            chain.doFilter(request, response, chain);
            response.res("CryFilter ");
        }
    }

    static class FilterChain implements Filter {
        List<Filter> filters = new LinkedList<>();

        public void add(Filter filter) {
            filters.add(filter);
        }

        int index = 0;

        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            if (index == filters.size()) return;

            Filter f = filters.get(index++);
            f.doFilter(request, response, chain);
        }
    }

    public static void main(String[] args) {
        FilterChain chain = new FilterChain();
        SmileFilter smileFilter = new SmileFilter();
        CryFilter cryFilter = new CryFilter();
        chain.add(smileFilter);
        chain.add(cryFilter);

        Request req = new Request();
        Response res = new Response();
        chain.doFilter(req, res, chain);
    }
}
