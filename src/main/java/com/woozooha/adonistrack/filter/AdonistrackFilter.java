/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.woozooha.adonistrack.filter;

import com.woozooha.adonistrack.aspect.ProfileAspect;
import com.woozooha.adonistrack.domain.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdonistrackFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);

            return;
        }

        chain.doFilter(request, response);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Invocation invocation = before(request);

        try {
            chain.doFilter(request, response);
        } finally {
            after(invocation, request, response);
        }
    }

    private Invocation before(HttpServletRequest request) {
        Event<RequestInfo> event = new RequestEvent(new RequestInfo(request));

        return ProfileAspect.before(event);
    }

    private void after(Invocation invocation, HttpServletRequest request, HttpServletResponse response) {
        Event<ResponseInfo> event = new ResponseEvent(new ResponseInfo(response));

        ProfileAspect.after(invocation, event);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
