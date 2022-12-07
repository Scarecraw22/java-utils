package io.github.scarecraw22.utils.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scarecraw22.utils.file.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MvcRequestBuilder {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final Map<String, String> headers = new HashMap<>();

    private boolean withRequestBuilder = false;
    private MockHttpServletRequestBuilder requestBuilder;

    private String url;
    private HttpMethod httpMethod;
    private MediaType contentType;
    private String body;

    public MvcRequestBuilder withMethod(HttpMethod method) {
        this.httpMethod = method;
        return this;
    }

    public MvcRequestBuilder post() {
        this.httpMethod = HttpMethod.POST;
        return this;
    }

    public MvcRequestBuilder patch() {
        this.httpMethod = HttpMethod.PATCH;
        return this;
    }

    public MvcRequestBuilder put() {
        this.httpMethod = HttpMethod.PATCH;
        return this;
    }

    public MvcRequestBuilder get() {
        this.httpMethod = HttpMethod.GET;
        return this;
    }

    public MvcRequestBuilder delete() {
        this.httpMethod = HttpMethod.DELETE;
        return this;
    }

    public MvcRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public MvcRequestBuilder contentType(MediaType contentType) {
        this.contentType = contentType;
        return this;
    }

    public MvcRequestBuilder withHeaders(Map<String, String> headers) {
        this.headers.clear();
        this.headers.putAll(headers);
        return this;
    }

    public MvcRequestBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public MvcRequestBuilder withBodyFromFile(String pathToFile) {
        this.body = FileUtils.readFileToString(Paths.get(pathToFile));
        return this;
    }

    public MvcRequestBuilder withRequestBuilder(MockHttpServletRequestBuilder requestBuilder) {
        this.withRequestBuilder = true;
        this.requestBuilder = requestBuilder;
        return this;
    }

    public MvcResponseChecker execute() throws Exception {
        if (withRequestBuilder) {
            return new MvcResponseChecker(mockMvc.perform(requestBuilder), objectMapper);
        } else {
            if (httpMethod == null) {
                throw new IllegalStateException("HttpMethod is not set. Use method() method on: " + MvcRequestBuilder.class.getName() + " class");
            }
            if (url == null || url.isBlank()) {
                throw new IllegalStateException("Url is not set. Use url method on: " + MvcRequestBuilder.class.getName() + " class");
            }
            if (contentType == null) {
                contentType = MediaType.APPLICATION_JSON;
            }
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(httpMethod, url)
                    .contentType(contentType);

            requestBuilder = addHeaders(requestBuilder, headers);
            if (body != null) {
                requestBuilder = requestBuilder.content(body);
            }

            return new MvcResponseChecker(mockMvc.perform(requestBuilder), objectMapper);
        }
    }

    private MockHttpServletRequestBuilder addHeaders(MockHttpServletRequestBuilder builder,
                                                     Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder = builder.header(entry.getKey(), entry.getValue());
            }
        }
        return builder;
    }
}
