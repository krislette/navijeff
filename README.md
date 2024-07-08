<a id="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/krislette/jeepney-navigation">
    <img src="src/images/icon.jpeg" alt="Logo" width="80" height="80">
  </a>

  <h1 align="center">NaviJeff</h1>
  <p align="center">
    Your Commuting Buddy
    <br />
    <a href="https://github.com/krislette/jeepney-navigation"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/krislette/jeepney-navigation">View Demo</a>
    ·
    <a href="https:///jeepney-navigation/issues">Report Bug</a>
    ·
    <a href="https://github.com/krislette/jeepney-navigation/issues">Request Feature</a>
  </p>
</div>

<div align="center">
  <img src="https://github.com/krislette/jeepney-navigation/assets/143507354/5df67d7a-e259-41dd-9430-09e3ca9a199a" alt="Demo" width="800">
</div>

<!-- ABOUT THE PROJECT -->
## About The Project

> NaviJeff is aimed at commuters residing within and outside Northern Metro Manila.
> It seeks to improve the current jeepney routing system for Northern Metro Manila, 
> especially focusing on optimizing travel costs and even travel time when hopping 
> from city to city.

<!-- TABLE OF CONTENTS -->
### Table Of Contents
<ol>
  <li>
    <a href="#about-the-project">About The Project</a>
    <ul>
      <li><a href="#table-of-contents">Table Of Contents</a></li>
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
      <li><a href="#cloning-the-repository-using-cmd">Cloning the Repository</a></li>
      <li><a href="#configure-api-key">Configuring API Key</a></li>
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

### Features
- Landing page with user input
- Routing page with user input and routing display
- Map page for showing fourteen jeepney stations
- Pretty and user-friendly UI and UX
- Map auto pan and map zoom in/out
- Sattelite view
- Dark mode
- Functional routing system using A* algorithm

### Technologies Used
NaviJeff uses a number of technologies to work properly:
- [NetBeans IDE](https://netbeans.apache.org/front/main/index.html)
- [JSON Jar](https://jar-download.com/artifacts/org.json/json/20210307/source-code)
- [Scene Builder](https://gluonhq.com/products/scene-builder/)
- [Java FX](https://openjfx.io/)
- [Maps Javascript API](https://developers.google.com/maps)

<!-- APPLICATION SNAPSHOTS -->
## Application Snapshots

### Landing Page
![Landing Page](https://github.com/krislette/jeepney-navigation/assets/143507354/b8b6898c-2fc9-42f2-9d4b-457a0d87c95f)

### Routing Page
![Routing Page](https://github.com/krislette/jeepney-navigation/assets/143507354/5b4801a1-dc70-4858-a9bd-8711dc9ac2aa)

### Maps Page
![Maps Page](https://github.com/krislette/jeepney-navigation/assets/143507354/afb17d59-981a-4ab6-9796-f05a99cd3afd)

### Satellite View
![Satellite View](https://github.com/krislette/jeepney-navigation/assets/143507354/2efad7e4-c888-44aa-9769-e7ef28212c13)

### Dark Mode
![Dark Mode](https://github.com/krislette/jeepney-navigation/assets/143507354/5987158b-e745-49f1-858a-0e02cf8a9db6)

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
    ├── images                        # All styling images used in the app
    └── places                        # All place images used in the app
    README.md

<!-- INSTALLATION -->
## Installation

### Prerequisites
- Ensure you have **Git** installed on your local machine. If not, download and install.
- Ensure you have **NetBeans IDE** on your local machine. If not, download and install.

### Cloning the repository using cmd
Cloning using `cmd` is not recommended, as NetBeans IDE has a built-in GUI for cloning.

<details>
<summary><b>Show Instructions</b></summary>
  
1. Fork this repository.

2. Open your terminal by typing `cmd` on your search bar.

3. Navigate to the directory where you want to clone the repository using the `cd` command:
   
    ```bash
    cd path/to/desired/directory
    ```

4. Clone the repository:
   
    ```bash
    git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git
    ```

</details>

### Cloning the repository using NetBeans
This is the recommended way of cloning this repository.

<details>
<summary><b>Show Instructions</b></summary>

1. Fork this repository.

3. Open NetBeans IDE.

4. Go to `Team` > `Remote` > `Clone`.

5. On the `Repository URL`, enter the url of your forked repository.
   
    ```bash
    https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git
    ```

4. Click `Next >` and tick the checkbox `main` that will apear.

5. Click `Finish`.

</details>

### Open the Project
Opening the project on your local machine depends on how you forked this repository.

<details>
<summary><b>Show Instructions</b></summary>

- If you used the `cmd` to clone your forked repository, open NetBeans and click `Open Project`. From there, find the directory of your forked repository and open it.

- If you used `NetBeans` to clone your forked repository, simply click `Open Project` on the dialog that will appear after cloning.

> After opening the project, you should see all the files listed on 
> the [Folder Structure](#Folder-Structure), but without the `config.js` file.

</details>

### Configure API Key
An **API key** is the most important part of this application, so make sure that you have it, from Google. To know more about getting your own API key, you can check this [video](https://youtu.be/hsNlz7-abd0?si=G-JMXV_MzokUXIEL). You can also contact the [developers](#contributors) to borrow the API key that they have used. After getting your own API key, you can proceed with the instructions.

<details>
<summary><b>Show Instructions</b></summary>

1. Create a `config.js` file under the backend folder.

2. Type the code below on the `config.js` file you just created. Make sure to replace `YOUR_API_KEY_HERE` with your actual **API key**.
   
    ```javascript
    const GOOGLE_MAPS_API_KEY = "YOUR_API_KEY_HERE";
    ```

</details>

### JavaFX Integration
To run the application, you should have **JavaFX** installed within your system. To know more about JavaFX installation, check this [video](https://www.youtube.com/watch?v=Iun8wEtn4Zs&t=1s).

<details>
<summary><b>Show Instructions</b></summary>

1. Once you have JavaFX installed and configured on your NetBeans IDE, go to the project, right-click and click `Properties`.

2. A dialog box will appear, click `Libraries` and then click the `+` button on the right of `Classpath`.

3. Click `Add Library`, a dialog box will appear again, and navigate through your available libraries. From there, find the **JavaFX library** you created by following this [video](https://www.youtube.com/watch?v=Iun8wEtn4Zs&t=1s).

4. After finding the **JavaFX library** you have installed, click `Add Library`. You should now see the JavaFX library under your `Classpath`.

5. Before clicking `OK`, click the `Java Platform` dropdown on top, and select the **JDK FX** that you have installed by following the [video](https://www.youtube.com/watch?v=Iun8wEtn4Zs&t=1s). After selecting the correct platform, you can now click `OK` to finalize and save the changes.

</details>

### JSON Jar Integration
JSON Jar is an essential component of this application. It is crucial for Java to accurately read our `geopositions.json` file.

<details>
<summary><b>Show Instructions</b></summary>

1. Go to your project, right-click and click `Properties`.

2. A dialog box will appear, click `Libraries` and then click the `+` button on the right of `Classpath`.

3. Open the project from the directory dialog that will appear, and find the `json-20210307.jar` file that is included when you forked this repository.

4. Click the jar file and click `Open`. You should now see the `json-20210307.jar` file under your `Classpath`, and then click `OK`.

</details>

### Scene Builder Integration (Optional)
If you want to modify the .fxml files (UI), you should have a [Scene Builder](https://gluonhq.com/products/scene-builder/) installed.

<details>
<summary><b>Show Instructions</b></summary>

1. To add **Scene Builder** to your IDE, click `Tools` on the menu bar of your NetBeans IDE. 

2. Click `Options`, a dialog box will appear. From there click `Java`. 

3. Under the `Scene Builder Home` dropdown menu click `Browse` and navigate to your Scene Builder installed folder.

4. Click `OK` and tick the checkbox below the dropdown menu. 

5. Click `Apply` on the bottom of the dialog box, and then click `OK` to close the dialog box.

</details>

<!-- RUN -->
## Run
To run the program, right-click the project name on your NetBeans IDE and click `Clean and Build`. Afterwards, just right-click the project name again and then click `Run`. 

<!-- LICENSE -->
## Contributors
**We would like to thank the following contributors for their support and contributions to this project:**

<div style="display: flex; align-items: center;">
  <div style="margin-right: 20px;">
    <a href="https://github.com/krislette"><img src="https://avatars.githubusercontent.com/u/143507354?v=4" title="acelle" width="50" height="50"></a>
  </div>
  <div>
    <strong>Acelle</strong>
    <p>Created the routing paths, JSON data, map page backend, drawing markers, fetching the API key, creating README, and finishing touches.</p>
  </div>
</div>

<div style="display: flex; align-items: center;">
  <div style="margin-right: 20px;">
    <a href="https://github.com/perse-v"><img src="https://avatars.githubusercontent.com/u/70996290?v=4" title="david" width="50" height="50"></a>
  </div>
  <div>
    <strong>David</strong>
    <p>Created the algorithm, javascript-java interaction, routing page backend, fixing program logic, and finalizing the map.</p>
  </div>
</div>

<div style="display: flex; align-items: center;">
  <div style="margin-right: 20px;">
    <a href="https://github.com/JLS0110"><img src="https://avatars.githubusercontent.com/u/117716525?v=4" title="jl" width="50" height="50"></a>
  </div>
  <div>
    <strong>John Lloyd</strong>
    <p>Created the wireframes, landing page UI, and collating frontend resources from other frontend developers.</p>
  </div>
</div>

<div style="display: flex; align-items: center;">
  <div style="margin-right: 20px;">
    <a href="https://github.com/sophiarya"><img src="https://avatars.githubusercontent.com/u/114216486?v=4" title="sophia" width="50" height="50"></a>
  </div>
  <div>
    <strong>Sophia</strong>
    <p>Created the initial wireframes that were adapted to the application, router page UI, and map page UI.</p>
  </div>
</div>

<div style="display: flex; align-items: center;">
  <div style="margin-right: 20px;">
    <a href="https://github.com/pj-drama"><img src="https://avatars.githubusercontent.com/u/155222986?v=4" title="rijan" width="50" height="50"></a>
  </div>
  <div>
    <strong>Rijan</strong>
    <p>Created the landing page UI, logos and design components, and added ideas for the application flow.</p>
  </div>
</div>

## License
Distributed under the [MIT](https://choosealicense.com/licenses/mit/) License. See [LICENSE](LICENSE) for more information.

<p align="right">[<a href="#readme-top">Back to top</a>]</p>
