//package com.hamch.orderserviceb.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
////import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//
//import feign.RequestInterceptor;
//
//public class OAuthFeignConfig {
//
//    public static final String CLIENT_REGISTRATION_ID = "keycloak";
//
//    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
//    private final ClientRegistrationRepository clientRegistrationRepository;
//
//    public OAuthFeignConfig(OAuth2AuthorizedClientService oAuth2AuthorizedClientService, ClientRegistrationRepository clientRegistrationRepository) {
//    	
//    	this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
//        this.clientRegistrationRepository = clientRegistrationRepository;
//        
//        System.out.printf(this.oAuth2AuthorizedClientService + "LLLLLLxxxxxxxxxxxxxxxxxxxxxxxxxxLLLLLLLLLLLL" + this.clientRegistrationRepository);
//    }
//    
////    @Bean
////    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
////        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
////    }
//
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(CLIENT_REGISTRATION_ID);
//        OAuthClientCredentialsFeignManager clientCredentialsFeignManager =
//            new OAuthClientCredentialsFeignManager(authorizedClientManager(), clientRegistration);
//        return requestTemplate -> {
//            requestTemplate.header("Authorization", "Bearer " + clientCredentialsFeignManager.getAccessToken());
//        };
//    }
//
//    @Bean
//    OAuth2AuthorizedClientManager authorizedClientManager() {
//        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
//            .clientCredentials()
//            .build();
//
//        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
//            new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//        return authorizedClientManager;
//    }
//}