package com.girgin.ramazan.springsecurityoauth2publickey.infrastructure.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExampleRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_get_hello_with_oauth2_authentication() throws Exception {
        //given
        String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NjM1NjQ5OTUsInVzZXJfbmFtZSI6InVzZXJhIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9DIiwiUk9MRV9BIiwiUk9MRV9CIl0sImp0aSI6IjE1MzU1MTE2LTE0N2YtNDg3My1iODFhLTBiMmU3NjhjYjMwMiIsImNsaWVudF9pZCI6IndlYi1hcHAiLCJzY29wZSI6WyJyZWFkIl19.OdeTXDpuuLD7GD05-WaoAmRTUg7CTCCHzmF2wjZKnnr0BzwgSFSXO_w2LUFkW967LZrRIVZs0y2IrPTzaVWyl24NB-uVNS_O_9JKI3tL5rYUTE-RhK_IIniOOvey5IF34uEMVcApOugmJnmocrFUSUQLzbTVGGlwRdtJFveHLKUxVKZf9iIIh_HCmUQiR3j00uq0yhIRHND4a5ZpcMyxEZQ3xHwHPkMZIPsbp3qbJ-CiUGJD2bgQgZ76oWrwEFpLDbsJ2CK6g9d7QYC3xLdAAyTroxHqHdvny2udnG6D0r8X0H4oj3TpX0cLKH8dq01MXzAn9jzidvJRr0_Ao4BSrQ";

        //when
        mockMvc.perform(get("/hello-role-a")
                .header("Authorization", "Bearer " + accessToken))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Hello-Role-A, Your User Name: usera")));
    }

    @Test
    public void should_return_unauthorized_response_when_oauth2_header_not_present() throws Exception {
        //given-when
        mockMvc.perform(get("/hello-role-a")
                .accept(MediaType.APPLICATION_JSON))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("unauthorized")));
    }

    @Test
    public void should_return_unauthorized_response_when_role_is_missing() throws Exception {
        //given-when
        mockMvc.perform(get("/hello-role-c")
                .accept(MediaType.APPLICATION_JSON))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("unauthorized")));
    }

    @Test
    public void should_return_invalid_token_response_when_token_signature_invalid() throws Exception {
        //given
        String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4NjM1NjQ5OTUsInVzZXJfbmFtZSI6InVzZXJhIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9DIiwiUk9MRV9BIiwiUk9MRV9CIl0sImp0aSI6IjE1MzU1MTE2LTE0N2YtNDg3My1iODFhLTBiMmU3NjhjYjMwMiIsImNsaWVudF9pZCI6IndlYi1hcHAiLCJzY29wZSI6WyJyZWFkIl19.XU7z5NDZlicvOFDUCRz6IZD6R5uHIZ2G4pHwQvdd9lGcGAWf6ZcT3CPFLNSmSmcfJvsetbp2B3Kfl9vbVUG47yMWC4zb68Jr6Lmn6Z0xm88Y6DjoNRAQLdmAOrUNwpGOj9LAxmLRghqFzs5OxSB8EXdxL_5_3Gndv92SZD9F6CUQbwCGLp1GvPhp0NwiQwKW8cmONlUrXqYQZS2b67vP7EmnlH2sqJ0RLMvFZWBAb81E94eFr7gswYW6XWriSQk053IGaQcYu48YxcuBfBnsZxDi6gvKOIFR__MnwpsorUGq-xynbuTJd6Y1Ex3kh3ketR3LKHrbIdwFQcktxLjHTA";

        //when
        mockMvc.perform(get("/hello-role-a")
                .header("Authorization", "Bearer " + accessToken))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("invalid_token")))
                .andExpect(jsonPath("$.error_description", is("Cannot convert access token to JSON")));
    }

    @Test
    public void should_get_non_secure_hello_without_authentication() throws Exception {
        //given-when
        mockMvc.perform(get("/non-secure-hello")
                .accept(MediaType.APPLICATION_JSON))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Hello-Non-Secure")));
    }
}