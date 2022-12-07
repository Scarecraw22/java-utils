package io.github.scarecraw22.utils.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.scarecraw22.utils.file.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Paths;

@RequiredArgsConstructor
public class MvcResponseChecker {

    private final ResultActions resultActions;
    private final ObjectMapper objectMapper;

    public MvcResponseChecker expectStatus(int statusCode) throws Exception {
        this.resultActions.andExpect(MockMvcResultMatchers.status().is(statusCode));
        return this;
    }

    public MvcResponseChecker expectStatus(HttpStatus httpStatus) throws Exception {
        return expectStatus(httpStatus.value());
    }

    public MvcResponseChecker expectOk() throws Exception {
        return expectStatus(HttpStatus.OK);
    }

    public MvcResponseChecker expectBadRequest() throws Exception {
        return expectStatus(HttpStatus.BAD_REQUEST);
    }

    public MvcResponseChecker expectNotFound() throws Exception {
        return expectStatus(HttpStatus.NOT_FOUND);
    }

    public MvcResponseChecker expectConflict() throws Exception {
        return expectStatus(HttpStatus.CONFLICT);
    }

    public MvcResponseChecker expectJson(String body) throws Exception {
        this.resultActions.andExpect(MockMvcResultMatchers.content().json(body));
        return this;
    }

    public MvcResponseChecker expectJsonFromFile(String pathToFile) throws Exception {
        return expectJson(FileUtils.readFileToString(Paths.get(pathToFile)));
    }

    public String getResponseBodyAsString() throws Exception {
        return this.resultActions.andReturn()
                .getResponse()
                .getContentAsString();
    }

    public <T> T getResponseBodyAs(Class<T> clazz) throws Exception {
        return objectMapper.readValue(getResponseBodyAsString(), clazz);
    }

    public MvcResponseChecker expectBodyFile(String pathToFile) throws Exception {
        return expectBody(FileUtils.readFileToString(Paths.get(pathToFile)));
    }

    public MvcResponseChecker expectBody(String body) throws Exception {
        this.resultActions.andExpect(MockMvcResultMatchers.content().string(body.trim()));
        return this;
    }
}
