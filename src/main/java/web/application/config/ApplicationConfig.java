package web.application.config;


public record ApplicationConfig(
        int httpPort,
        Environment env
) {
    public ApplicationConfig {
        if (httpPort < 1 || httpPort > 65535) {
            throw new IllegalArgumentException("Invalid HTTP port number: " + httpPort);
        }
        if(env == null) {
            throw new IllegalArgumentException("Environment may not be null");
        }
    }
}


