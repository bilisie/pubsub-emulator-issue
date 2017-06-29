import static com.google.api.client.googleapis.testing.auth.oauth2.MockGoogleCredential.newMockHttpTransportWithSampleTokenResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.testing.auth.oauth2.MockGoogleCredential;
import com.spotify.google.cloud.pubsub.client.Subscription;
import java.net.URI;
import com.spotify.google.cloud.pubsub.client.Pubsub;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class EmulatorTest {

  private static final String DEFAULT_TARGET = "http://localhost:8085/v1/";

  public static void main(String[] args)
      throws URISyntaxException, ExecutionException, InterruptedException {
    String target = parseOrDefault(args, DEFAULT_TARGET);
    Pubsub pubsub = pubsub(target);
    Subscription subscription = pubsub.createSubscription("sample_project",
        "subscriptionName",
        "topic").get();
    System.out.println("Successful subscription: " + subscription);
  }

  private static Pubsub pubsub(String target) throws URISyntaxException {
    return Pubsub.builder()
        .credential(credentials())
        .uri(new URI(target)).build();
  }

  private static Credential credentials() {
    MockGoogleCredential mockGoogleCredential =
        new MockGoogleCredential.Builder()
            .setTransport(newMockHttpTransportWithSampleTokenResponse()).build();
    mockGoogleCredential.setAccessToken("sample");
    return mockGoogleCredential;

  }

  private static String parseOrDefault(String[] args, String defaultTarget) {
    if (args.length == 0) {
      return defaultTarget;
    }
    return args[0];
  }

}
