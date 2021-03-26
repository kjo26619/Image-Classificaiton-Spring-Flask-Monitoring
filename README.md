# Image-Classificaiton-Spring-Flask-Monitoring

Image classification TOY project using Java Spring Web, Python Flask REST API and Docker Compose.

https://github.com/kjo26619/Image-Classification-Spring-Flask

plus, add monitoring tools (Prometheus + Grafana)

# Architecture

![imageArch](https://github.com/kjo26619/Image-Classification-Spring-Flask/blob/main/img/arch.png)

기존 구조에서 Container로 Prometheus와 Grafana가 추가된다.

# Flask Server + Prometheus

Flask 서버에 Prometheus의 Metric을 반환하기 위해 prometheus_flask_exporter 를 사용한다.

```
import Flask
...
from prometheus_flask_exporter import PrometheusMetrics

app = Flask(__name__)
metrics = PrometheusMetrics(app)

...

```

로컬로 확인한다면 localhost:5000/metrics로 들어가면 결과가 반환되는 것을 확인해볼 수 있다.

![image1](https://github.com/kjo26619/Image-Classification-Spring-Flask/blob/main/img/flask.PNG)

# Prometheus + Grafana

Prometheus와 Grafana Container를 추가한다.

자세한 설명은 https://medium.com/swlh/generate-and-track-metrics-for-flask-api-applications-using-prometheus-and-grafana-55ddd39866f0 에서 확인해볼 수 있다.

./monitoring/ 에 Prometheus와 Grafana 설정 파일을 추가한다.

- prometheus.yaml : Prometheus의 설정 파일
- datasource.yaml : Grafana의 설정 파일
- config.monitoring : Grafana의 로그인 설정 파일

# Docker-compose up

Docker Compose를 이용할 때 Prometheus와 Grafana가 같이 시작되도록 compose YAML 파일을 수정한다.

```
...

  ic_prometheus:
    image: prom/prometheus
    ports:
      - 9090:9090
    volumes:
      - ./monitoring/prometheus.yaml:/etc/prometheus/prometheus.yaml
    command:
      - '--config.file=/etc/prometheus/prometheus.yaml'
    networks:
      - ic_network
  
  ic_grafana:
    image: grafana/grafana
    ports: 
      - 8083:3000
    networks:
      - ic_network
      - default
    volumes:
      - ./monitoring/datasource.yaml:/etc/grafana/provisioning/datasource.yaml
    env_file:
      - ./monitoring/config.monitoring
    depends_on:
      - ic_flask_api
      - ic_prometheus
...
```

이처럼 VOLUME 과 COMMAND, ENV_FILE 연결을 해도 되며 이미지를 새로 Build해서 COPY, CMD, ENV를 사용해도 상관없다.

Docker Compose가 완료되면 localhost:8083으로 접속하면 Grafana Dashboard와 연결할 수 있다.

![image2](https://github.com/kjo26619/Image-Classification-Spring-Flask/blob/main/img/grafana1.png)

Datasource로 들어가서 Prometheus를 추가한다.

![image3](https://github.com/kjo26619/Image-Classification-Spring-Flask/blob/main/img/grafana2.PNG)

Prometheus는 Docker compose를 지정할 때 사용했던 Container 이름을 지정하면 Docker DNS로 연결할 수 있다.

![image4](https://github.com/kjo26619/Image-Classification-Spring-Flask/blob/main/img/grafana3.PNG)

[Save & Test] 를 눌러서 Data source is working이 나오면 된다.

![image5](https://github.com/kjo26619/Image-Classification-Spring-Flask/blob/main/img/grafana4.PNG)

localhost:8082에 접속하여 이미지를 업로드하면 Classification 되면서 Request가 증가한다.

이 Dashboard 형식은 https://github.com/rycus86/prometheus_flask_exporter/blob/master/examples/sample-signals/grafana/dashboards/example.json 이다.

Dashboard를 추가한 뒤 Settings에서 JSON 모델을 바꿔주면 된다.

# Reference

- Imagenet : http://www.image-net.org/
- Imagenet Clsidx to Labels : https://gist.github.com/yrevar/942d3a0ac09ec9e5eb3a
- Pytorch MobileNet V2 : https://pytorch.org/hub/pytorch_vision_mobilenet_v2/
- Spring with Docker : https://spring.io/guides/gs/spring-boot-docker/
- Python with Docker : https://docs.docker.com/language/python/build-images/
- Maven build : https://sarc.io/index.php/cloud/1856-spring-boot-docker
- Prometheus Flask : https://pypi.org/project/prometheus-flask-exporter/
- Prometheus + Grafana : https://medium.com/swlh/generate-and-track-metrics-for-flask-api-applications-using-prometheus-and-grafana-55ddd39866f0
- Grafana Dashboard Example JSON : https://github.com/rycus86/prometheus_flask_exporter/blob/master/examples/sample-signals/grafana/dashboards/example.json

