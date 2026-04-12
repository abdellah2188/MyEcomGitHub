


# Stage 1: Compile and Build angular codebase

# Use official node image as the base image
FROM node:22.14.0-alpine AS build

# Set the working directory

WORKDIR /usr/src/app
COPY package.json package-lock.json ./
# Add the source code to app

COPY ./ /usr/src/app/
RUN npm install -g @angular/cli
# Install all the dependencies
RUN npm install --legacy-peer-deps

# Generate the build of the application
RUN ng build


# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:stable
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
#Copy the build output to replace the default nginx contents.
COPY --from=build /usr/src/app/dist/ng-keycloak/browser /usr/share/nginx/html

# Expose port 80
EXPOSE 80
#CMD ["nginx","-g","daemon off;"]