[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/shubhamlatkar/foodgrid)
# food-grid

## Food Delivery App

To run with docker

```
git clone http://github.com/shubhamlatkar/foodgrid.git && cd foodgrid/ && git pull origin notification-impl && git checkout notification-impl && docker-compose up
```

### Useful docker commands

docker stop $(docker ps -a -q)

1. Remove all images `docker image prune -a`.
2. Stop all container `docker stop $(docker ps -a -q) `.
3. Delete all stopped containers `docker rm $(docker ps -a -q)`.
4. Delete all stopped images `docker rmi $(docker images -q)`.

#### Ref
1. [Docker ref](https://www.codenotary.com/blog/extremely-useful-docker-commands/)