FROM gradle:jdk17-alpine as base

COPY ./app/ /home/gradle/
RUN gradle clean buildDependents

# ENTRYPOINT gradle

FROM base as production

CMD gradle run

FROM base as test

RUN gradle test
