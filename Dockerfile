FROM gradle:jdk17-alpine as base

COPY ./app/ /home/gradle/
RUN gradle clean buildDependents

# ENTRYPOINT gradle

FROM base as production

CMD gradle run

FROM base as development

RUN gradle test
# ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=16476,server=y,suspend=n
