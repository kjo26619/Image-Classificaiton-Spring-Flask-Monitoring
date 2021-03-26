FROM python:3.7.10-slim-buster

COPY ./ /application
WORKDIR /application

RUN pip3 install -r requirements.txt

ENTRYPOINT ["python3"]

CMD ["main.py"]
