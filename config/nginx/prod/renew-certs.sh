#!/bin/bash
certbot renew
cp /etc/letsencrypt/live/${DOMAIN}/fullchain.pem ssl/
cp /etc/letsencrypt/live/${DOMAIN}/privkey.pem ssl/
docker compose -f compose.prod.yaml restart nginx-prod