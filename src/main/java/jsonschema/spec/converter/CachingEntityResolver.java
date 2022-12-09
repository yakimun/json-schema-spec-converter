package jsonschema.spec.converter;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CachingEntityResolver implements EntityResolver {
    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws IOException {
        var cacheDirPath = Paths.get("cache");
        if (Files.notExists(cacheDirPath)) {
            Files.createDirectory(cacheDirPath);
        }

        var uri = URI.create(systemId);
        var uriPath = uri.getPath().split("/");
        var path = cacheDirPath.resolve(uriPath[uriPath.length - 1]);

        if (Files.notExists(path)) {
            var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            try {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                Files.write(path, response.body().getBytes());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return new InputSource(Files.newInputStream(path));
    }
}
