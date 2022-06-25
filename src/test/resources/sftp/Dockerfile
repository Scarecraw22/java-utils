FROM ubuntu:18.04
RUN apt-get update
RUN apt-get install -y openssh-server
RUN mkdir /var/run/sshd

RUN echo 'root:root' |chpasswd

RUN sed -i 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -i 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

RUN mkdir /root/.ssh
RUN apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

ENV NOTVISIBLE "in users profile"
RUN echo "export VISIBLE=now" >> /etc/profile
EXPOSE 22

CMD    ["/usr/sbin/sshd", "-D"]
#FROM ubuntu:latest
#
#RUN apt update && apt install openssh-server sudo -y
#
#RUN useradd -rm -d /home/ubuntu -s /bin/bash -g root -G sudo -u 1000 test
#
#RUN  echo 'test:test' | chpasswd
#
#RUN service ssh start
#
#EXPOSE 22
#
#CMD ["/usr/sbin/sshd","-D"]
