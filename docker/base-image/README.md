# Flowable Docker base image

Extends from eclipse-temurin:17.0.9_9-jre-alpine

Adds `flowable:flowable` user which can be used to 'step down' from root when executing Flowable applications.

#### modifications

* `RUN apk add --no-cache ttf-dejavu su-exec && rm -rf /var/cache/apk/*`
* `RUN addgroup -S flowable && adduser -S flowable -G flowable`
* `ADD wait-for-something.sh .`
* `RUN chmod +x wait-for-something.sh`
