# kube-ms-distributed
This application is developed for the Kubernetes Microservices workshop.
To start with the application we are using H2 in memory database.
 
# Access API documentation
1. Open the application url. It will show the welcome message. There is a link for api documentation and it would open the API documentation page.
 
# PCF Deployment
If you want deploy the application on Pivotal Cloud Foundry you have to make the following set up. 

1. Create one mysql service instance and bind it to your application.
2. Create the few environmental variables executing the following command. Use the first command only if you are using jdk11. The other two commands would enable the actuator endpoints. 
   - cf set-env <app-name> JBP_CONFIG_OPEN_JDK_JRE '{ jre: { version: 11.+ } }'
   - cf set-env <app-name> MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE '*'
   - cf set-env <app-name> MANAGEMENT_ENDPOINT_HEALTH_SHOWDETAILS 'always'
   - cf set-env <app-name> CATEGORY_URL <category app url>
# Docker Build
1. To build 2  docker images for business and category use the following command [Ensure that Docker Service is up and running]
   * docker build -f Dockerfile.bis -t adityapratapbhuyan/kube-ms-workshop:business-v1 . 
   * docker build -f Dockerfile.cat -t adityapratapbhuyan/kube-ms-workshop:category-v1 . 
   * docker images [It ensures the image is available in local docker repository]
2. To push the images to docker hub use the below command
   * docker push adityapratapbhuyan/kube-ms-workshop:business-v1
   * docker push adityapratapbhuyan/kube-ms-workshop:category-v1
3. To run the image in Kubernetes cluster use the below command. [For the example minikube is already running]
   * kubectl -f create deployment/business-deployment.yaml
   * kubectl -f create deployment/category-deployment.yaml
4. To access the applications we need to expose it as service and then we can access the service. Use the below commands for exposing the service and access the service.
   * kubectl -f create deployment/business-service.yaml
   * kubectl -f create deployment/category-service.yaml
   * minikube service business [This would open the browser and land on the index page of the application]
   * minikube service category [This would open the browser and land on the index page of the application]