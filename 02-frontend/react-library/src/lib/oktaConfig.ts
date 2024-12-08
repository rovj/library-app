export const oktaConfig = {
    clientId: '0oal6lwwjdMD9GvKu5d7',
    issuer: 'https://dev-69811998.okta.com/oauth2/default',
    redirectUri: 'https://localhost:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true,
}