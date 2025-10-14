# Cymbal Eats (cymbal-eats)

An app for hungry people

Add a bucket that is public read + write.
"allUsers" should have the role "Storage Object Viewer".

## Install the dependencies
```bash
npm install
```

## Install quasar cli
```bash
npm install -g @quasar/cli
```

### Start the app in development mode (hot-code reloading, error reporting, etc.)
```bash
quasar dev
```

### Build the app for production
```bash
quasar build
```

### Customize the configuration
See [Configuring quasar.conf.js](https://quasar.dev/quasar-cli/quasar-conf-js).

Externalizing env config
```sh
quasar ext add @quasar/dotenv
```

### Build and push Docker image
```bash
quasar build

docker build -f Dockerfile --tag gcr.io/$PROJECT_NAME/cymbal-eats-client dist

docker push gcr.io/$PROJECT_NAME/cymbal-eats-client
```

### Deploy the app

Check/set REGION before deploying the app.

Example: REGION=us-east1
```bash
gcloud run deploy cymbal-eats-client \
--image=gcr.io/$PROJECT_NAME/cymbal-eats-client:latest \
--port=80 --region=$REGION \
--allow-unauthenticated \
-q
```


# UI to code
Analyze UI changes between current-ui.png and updated-ui.png images and provide summary.
apply changes in the employee-ui app


# UI tests automation
Check that menu item cards have rating property displayed on this page “https://employee-ui-service-713244360550.us-central1.run.app/0#/view-menu”. If its not there, open a new JIRA issue for the team to look into it. JIRA instance is genai4dev.atlassian.net. Close the browser after you are done.


# Security code scan
Run security code scan for employee-ui application and report issues.
