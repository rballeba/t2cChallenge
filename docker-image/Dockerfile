# Our image will start on Ubuntu
FROM postgres:latest
# We will we working on the project folder
WORKDIR ~/Project
# Updating apt
RUN apt update
# Installing APT utils
RUN apt install -y apt-utils
# Installing Wget
RUN apt install -y wget
# Installing VIM
RUN apt install -y nano
# Copying initialization scripts to docker
COPY ./sql/initUser.sql /docker-entrypoint-initdb.d/
COPY ./sql/sqlCreation.sql /docker-entrypoint-initdb.d/
COPY ./app /app
# Setting env vars
ENV POSTGRES_PASSWORD cars
