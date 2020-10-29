FROM prod.docker.system.local/sda/centos7-jre11:51

COPY ./build/install/* /java/
COPY ./config.yml /java/config/

RUN /tmp/setPermissions.sh
USER java
