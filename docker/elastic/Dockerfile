FROM docker.elastic.co/elasticsearch/elasticsearch:8.12.1

# anaylysis-nori 미설치 -> 설치
RUN if ! elasticsearch-plugin list | grep -q analysis-nori; then \
        elasticsearch-plugin install analysis-nori; \
    fi