<a id="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/your_username/repo_name">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">NaviJeff</h3>
  <p align="center">
    Your Commuting Buddy
    <br />
    <a href="https://github.com/your_username/repo_name"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/your_username/repo_name">View Demo</a>
    ·
    <a href="https://github.com/your_username/repo_name/issues">Report Bug</a>
    ·
    <a href="https://github.com/your_username/repo_name/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#features">Features</a></li>
        <li><a href="#technologies-used">Technologies Used</a></li>
      </ul>
    </li>
    <li>
      <a href="#application-snapshots">Application Snapshots</a>
    </li>
    <li>
      <a href="#folder-structure">Folder Structure</a>
    </li>
    <li>
      <a href="#installation">Installation</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#cloning-the-repository">Cloning the Repository</a></li>
        <li><a href="#configuring-api-key">Configuring API Key</a></li>
        <li><a href="#javafx-integration">JavaFX Integration</a></li>
        <li><a href="#json-jar-integration">JSON Jar Integration</a></li>
        <li><a href="#scene-builder-integration-optional">Scene Builder Integration (Optional)</a></li>
      </ul>
    </li>
    <li>
      <a href="#run">Run</a>
    </li>
    <li>
      <a href="#contributors">Contributors</a>
    </li>
    <li>
      <a href="#license">License</a>
    </li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

> NaviJeff is aimed at commuters residing within and outside Northern Metro Manila.
> It seeks to improve the current jeepney routing system for Northern Metro Manila, 
> especially focusing on optimizing travel costs and even travel time when hopping 
> from city to city.

<p align="right">[<a href="#readme-top">Back to top</a>]</p>

### Features
- Landing page with user input
- Routing page with user input and routing display
- Map page for showing fourteen jeepney stations
- Pretty and user-friendly UI and UX
- Map auto pan and map zoom in/out
- Functional routing system using A* algorithm

<p align="right">[<a href="#readme-top">Back to top</a>]</p>

### Technologies Used
NaviJeff uses a number of technologies to work properly:
- [NetBeans IDE](https://netbeans.apache.org/front/main/index.html)
- [JSON Jar](https://jar-download.com/artifacts/org.json/json/20210307/source-code)
- [Scene Builder](https://gluonhq.com/products/scene-builder/)
- [Java FX](https://openjfx.io/)
- [Maps Javascript API](https://developers.google.com/maps)

<p align="right">[<a href="#readme-top">Back to top</a>]</p>

<!-- APPLICATION SNAPSHOTS -->
## Application Snapshots

### Landing Page
![Landing Page](https://github.com/krislette/jeepney-navigation/assets/143507354/0715c5e4-51e0-4e87-88b2-ca5633119c4a)

### Routing Page
![Routing Page](https://github.com/krislette/jeepney-navigation/assets/143507354/35368003-8426-4d0b-bf0e-0e5017acc30b)

### Maps Page
![Maps Page](https://github.com/krislette/jeepney-navigation/assets/143507354/1a5a6a2e-a1a0-4c8c-851d-f02b1c9dc938)

### Satellite View
![Satellite View](https://github.com/krislette/jeepney-navigation/assets/143507354/e927fa62-a8ed-4ac8-bbb4-810275b81898)

<p align="right">[<a href="#readme-top">Back to top</a>]</p>

<!-- FOLDER STRUCTURE -->
## Folder Structure

    src
    ├── algorithm                     # Folder containing A* algorithm
    │ ├──── AStar.java                # A* algorithm code
    │ ├──── Edge.java                 # Edge configuration
    │ └──── Node.java                 # Node configuration
    ├── backend                       # Folder for API-related operations
    │ ├──── config.js                 # API
    │ ├──── geopositions.json         # Data for nodes and edges
    │ ├──── map.html                  # Show map
    │ └──── script.js                 # Map-related operations
    ├── frontend                      # Folder for UI and UX
    │ ├──── Controller.java           # Program logic
    │ ├──── GetRoute.fxml             # Routing page UI
    │ ├──── LandingPage.fxml          # Home page UI
    │ ├──── Main.java                 # Program entry point
    │ ├──── MapPage.fxml              # Map page UI
    │ └──── style.css                 # For additional styling
    └── images                        # All images used in the app
    README.md

<p align="right">[<a href="#readme-top">Back to top</a>]</p>

<!-- INSTALLATION -->
## Installation

### Prerequisites
- Ensure you have Git installed on your local machine. If not, download and install.
- Ensure you have NetBeans IDE on your local machine. If not, download and install.

### [1.1] Cloning the repository using cmd
1. Fork this repository

2. Open your terminal by typing `cmd` on your search bar.

3. Navigate to the directory where you want to clone the repository using the `cd` command.
```bash
cd path/to/desired/directory
```

4. Clone the repository.
```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git
```

### [1.2] Cloning the repository using NetBeans
1. Open NetBeans IDE.

2. Go to `Team` > `Remote` > `Clone`.

3. On the `Repository URL`, enter the url of your forked repository.
```bash
https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git
```

4. Click `Next >` and tick the checkbox `main` that will apear.

5. Click `Finish`.

### [2] Opening the project

- If you used the `cmd` to clone your forked repository, open NetBeans and click `Open Project`. From there, find the directory of your forked repository and open it.

- If you used `NetBeans` to clone your forked repository, simply click `Open Project` on the dialog that will appear after cloning.

> After opening the project, you should see all the files listed on 
> the [Folder Structure](#Folder-Structure), but without the `config.js` file.

### [3] Configuring API key

1. Create a `config.js` file under the `backend` folder.

2. Make sure that you have `API key` from Google. To know more about getting your own `API key`, you can see this [video](https://youtu.be/hsNlz7-abd0?si=G-JMXV_MzokUXIEL). After getting an `API key`, type the code below on the `config.js` file you just created.
```javascript
const GOOGLE_MAPS_API_KEY = "YOUR_API_KEY";
```

### [4] JavaFX Integration

To run the application, you should have JavaFX installed within your system. To know more about JavaFX installation, see this [video](https://www.youtube.com/watch?v=Iun8wEtn4Zs&t=1s).

1. Once you have `JavaFX` installed and configured on your `NetBeans IDE`, go to the project, right-click and click `Properties`.

2. A dialog box will appear, click `Libraries` and then click the `+` button on the right of `Classpath`.

3. Click `Add Library`, a dialog box will appear again, and navigate through your available libraries. From there, find the `JavaFX` library you created by following the video. 

4. After finding the `JavaFX` library you have installed, click `Add Library`. You should now see the `JavaFX` library under your `Classpath`.

5. Before clicking `OK`, click the `Java Platform` dropdown on top, and select the `JDK FX` that you have installed by following the video. After selecting the correct platform, you can now click `OK` to finalize the changes.

### [5] JSON Jar Integration

1. Go to your project, right-click and click `Properties`.

2. A dialog box will appear, click `Libraries` and then click the `+` button on the right of `Classpath`.

3. Open the project from the directory dialog that will appear, and find the `json-20210307.jar` file that is included when you forked this repository.

4. Click the jar file and click `Open`. You should now see the `json-20210307.jar` file under your `Classpath`, and then click `OK`.

### [6] Scene Builder Integration (Optional)

If you want to modify the .fxml files (UI), you should have a [Scene Builder](https://gluonhq.com/products/scene-builder/) installed.

1. To add `Scene Builder` to your IDE, click `Tools` on the menu bar of your NetBeans IDE. 

2. Click `Options`, a dialog box will appear. From there click `Java`. 

3. Under the `Scene Builder Home` dropdown menu click `Brows` and navigate to your `Scene Builder` installed folder.

4. Click `OK` and tick the checkbox below the dropdown menu. 

5. Click `Apply` on the bottom of the dialog box, and then click `OK` to close the dialog box.

<p align="right">[<a href="#readme-top">Back to top</a>]</p>

<!-- RUN -->
## Run

To run the program, right-click the project name on your NetBeans IDE and click `Clean and Build`. Afterwards, just right-click the project name again and then click `Run`. 

<p align="right">[<a href="#readme-top">Back to top</a>]</p>

<!-- LICENSE -->
## Contributors

We would like to thank the following contributors for their support and contributions to this project:

- [Acelle](https://github.com/krislette) for creating the routing paths using Directions API, JSON data, map page backend, drawing markers, fetching the API key, and finishing touches.

- [David](https://github.com/perse-v) for creating the algorithm, javascript-java interaction, routing page backend, fixing program logic, and finalizing the map.

- [John Lloyd](https://github.com/JLS0110) for creating the wireframes, [landing page](#Landing-Page) UI, and collating frontend resources from other frontend developers.

- [Sophia](https://github.com/sophiarya) for creating the initial wireframes that were adapted to the application, [router page](#Routing-Page) UI, and [map page](#Maps-Page) UI.

- [Rijan]() for creating the [landing page](#Landing-Page) UI, and adding ideas for the application flow.

## License

Distributed under the [MIT](https://choosealicense.com/licenses/mit/) License. See LICENSE.txt for more information.