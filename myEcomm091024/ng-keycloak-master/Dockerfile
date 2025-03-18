

# Stage 1: Compile and Build angular codebase

# Use official node image as the base image
FROM node:latest as build

# Set the working directory
#WORKDIR B:/Java091024/ANGULAR_DIR
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
# Add the source code to app
#COPY ./ D:/Java091024/ANGULAR_DIR/
COPY ./ /usr/src/app/
RUN npm install -g @angular/cli
# Install all the dependencies
RUN npm install

# Generate the build of the application
RUN ng build


# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:latest

# Copy the build output to replace the default nginx contents.
COPY --from=build /usr/src/app/dist/ng-keycloak /usr/share/nginx/html

# Expose port 80
EXPOSE 8001
