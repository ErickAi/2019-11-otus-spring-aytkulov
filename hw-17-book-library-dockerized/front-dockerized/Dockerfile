FROM node:current-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN yarn
COPY . .
RUN yarn build

FROM nginx:alpine
WORKDIR /app
COPY --from=build /app/dist/ .
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

# docker build . -t book-library-frontend --rm
# docker run --rm -it --name frontend -p 8079:80 -e book-library-frontend:latest

# docker build . --rm -t book-library-frontend && docker run --rm -it --name frontend -p 8079:80 book-library-frontend
