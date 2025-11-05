
// Bloque 1 Corregido:
// short period of time, then it will give stronger signals of possible abuse.
if (!isValidIpAddress(previousIpAddresses, requestIpAddress)) {
  // Invalid IP address, take action quickly and revoke all user's refresh tokens.
  revokeUserTokens(claims.uid).then(() => {
    res.status(401).send({error: 'Unauthorized access. Please login again!'});
  }, error => {
    res.status(401).send({error: 'Unauthorized access. Please login again!'});
  });
} else {
  // Access is valid. Try to return data.
  getData(claims).then(data => {
    res.end(JSON.stringify(data));
  }, error => {
    res.status(500).send({ error: 'Server error!' });
  });
}

// Bloque 2 Corregido:
// geographical change in a short period of time, then it will give
// stronger signals of possible abuse.
if (isSuspiciousIpAddressChange(signInIpAddress, requestIpAddress)) {
  // Suspicious IP address change. Require re-authentication.
  // You can also revoke all user sessions by calling:
  // admin.auth().revokeRefreshTokens(claims.sub).
  res.status(401).send({error: 'Unauthorized access. Please login again!'});
} else {
  // Access is valid. Try to return data.
  getData(claims).then(data => {
    res.end(JSON.stringify(data));
  }, error => {
    res.status(500).send({ error: 'Server error!' });
  });
}
