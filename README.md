# Artique

Artique is an Android application built using the MVVM (Model-View-ViewModel) architectural pattern. The app reads data from the device and displays your gallery items. 

## Prerequisites

- Android Studio.
- Android SDK version 24 or above.
- Kotlin 1.7.0 or above.

## Getting Started

To get started with the Artique project, follow these steps:

1. Clone the repository to your local machine using the following command:

    git clone https://github.com/abdullahHanif/Artique.git

2. Open the project in Android Studio.

3. Build the project to download dependencies and ensure everything is set up correctly.

4. Run the app on an emulator or a physical device.

## Project Structure

The Artique project follows a modularized structure, separating concerns to improve maintainability and scalability. The project consists of the following modules:

- **app**: Contains the main Android application module, including the UI components and business logic.

## Libraries Used

The Artique project utilizes the following libraries and frameworks:

- AndroidX libraries for Android support and Jetpack components.
- ViewModel and State flows for implementing the MVVM architecture.
- RecyclerView for displaying of Media.
- Glide for efficient image and video loading.
- Kotlin Coroutines for asynchronous and reactive programming.
- Dagger Hilt for dependency injection.

## Architecture Overview

Artique follows the MVVM architectural pattern, which separates the application into three main components:

- **Model**: Represents the data layer, including data sources, repositories, and entities. In this project, the model interacts with the content resolver to fetch folders, pictures, and videos.
- **View**: Represents the UI layer and displays the user interface. It consists of activities, fragments, and layouts responsible for rendering data and interacting with the user.
- **ViewModel**: Acts as a bridge between the model and the view, responsible for providing data to the view and handling user interactions. The ViewModel abstracts away the underlying data source and exposes the necessary data for the view to display.

## Contributing

Contributions to the Artique project are welcome. If you'd like to contribute, please follow these steps:

1. Fork the repository.

2. Create a new branch for your feature or bug fix.

3. Commit your changes and push the branch to your fork.

4. Submit a pull request, explaining your changes and the motivation behind them.

Please ensure that your code follows the existing code style and conventions. Include relevant tests and documentation for the changes you make.

## License

The Artique project is released under the [MIT License](https://opensource.org/licenses/MIT). Feel free to use, modify, and distribute the code as per the terms of the license.

## Contact

If you have any questions or suggestions regarding the Artique project, please feel free to contact the project maintainer at [abdullah.mamjee@gmail.com](mailto:abdullah.mamjee@gmail.com).

---

Thank you for your interest in the Artique project! We hope you find it helpful and enjoy contributing to its development.
