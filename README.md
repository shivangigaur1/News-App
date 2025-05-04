# Headlines360 - News App 

## Overview
**Headlines360** is a feature-rich Android news application that delivers the latest headlines with smart power-saving capabilities. The app automatically adapts to your device's battery level to optimize performance while providing a seamless news browsing experience.

---

## Key Features

### News Consumption
- Category-based news browsing (General, Business, Technology, etc.)
- Detailed article viewing with full content display
- Keyword + Date search for historical news articles
- Clean, responsive UI with smooth navigation

### Smart Features
- Auto theme switching using light sensor (Dark/Light mode)
- Text-to-Speech for hands-free news listening
- Power-saving mode that activates automatically when battery ≤ 20%:
  - Reduces sensor frequency
  - Disables TTS functionality
- Persistent preferences - remembers your favorite categories

---

## Technical Implementation 

### API Integration
The app uses **[NewsAPI.org](https://newsapi.org/)** with the following endpoints:
- `top-headlines` for category-based news
- `everything` for keyword/date searches


### Core Components
- **SensorHelper**: Manages light sensor for auto-theme switching
- **NewsAdapter**: Handles news display with TTS functionality
- **PowerHelper**: Implements battery-conscious optimizations
- **AppPreferences**: Manages user settings via `SharedPreferences`

---

## Power Saving Features 
The app automatically:
- Reduces sensor polling rate when battery ≤ 20%
- Disables TTS functionality in low-power mode
- Maintains core functionality while conserving energy

---

## Setup Instructions 

### Prerequisites
- Android Studio
- NewsAPI key (free tier available)
- Minimum SDK: API 23 (Android 6.0)

