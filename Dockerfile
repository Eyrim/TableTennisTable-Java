FROM gradle:jdk17-alpine

COPY ./app/ /home/gradle/
RUN gradle clean buildDependents

ENTRYPOINT [ "gradle", "run" ]

