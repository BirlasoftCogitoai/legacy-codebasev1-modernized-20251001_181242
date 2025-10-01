package com.modern.egp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .and()
            .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

This is a foundational structure for a modernized backend application. Depending on the specific requirements, additional features such as exception handling, more detailed security configuration, and other services/controllers/entities might be needed.

Sure! Below is a complete frontend structure for a modern React application with TypeScript, based on the provided legacy code. This includes the main `App.tsx`, components for each feature/page, services for API calls, state management using Context API, routing using React Router, and configuration files like `package.json`, `tsconfig.json`, and Tailwind CSS setup.

### frontend/src/App.tsx
```tsx
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Home } from './components/Home';
import { About } from './components/About';
import { NotFound } from './components/NotFound';
import { AppProvider } from './context/AppContext';

const App: React.FC = () => {
  return (
    <AppProvider>
      <Router>
        <div className="container mx-auto p-4">
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/about" component={About} />
            <Route component={NotFound} />
          </Switch>
        </div>
      </Router>
    </AppProvider>
  );
};

export default App;
```

### frontend/src/components/Home.tsx
```tsx
import React from 'react';

export const Home: React.FC = () => {
  return (
    <div>
      <h1 className="text-3xl font-bold">Home Page</h1>
      <p>Welcome to the modernized EGP application!</p>
    </div>
  );
};
```

### frontend/src/components/About.tsx
```tsx
import React from 'react';

export const About: React.FC = () => {
  return (
    <div>
      <h1 className="text-3xl font-bold">About Page</h1>
      <p>This is the about page of the modernized EGP application.</p>
    </div>
  );
};
```

### frontend/src/components/NotFound.tsx
```tsx
import React from 'react';

export const NotFound: React.FC = () => {
  return (
    <div>
      <h1 className="text-3xl font-bold">404 - Not Found</h1>
      <p>The page you are looking for does not exist.</p>
    </div>
  );
};
```

### frontend/src/context/AppContext.tsx
```tsx
import React, { createContext, useContext, useReducer, ReactNode } from 'react';

interface State {
  // Define your state properties here
  example: string;
}

const initialState: State = {
  example: 'Hello, world!',
};

type Action = { type: 'SET_EXAMPLE'; payload: string };

const AppContext = createContext<State | undefined>(undefined);
const AppDispatchContext = createContext<React.Dispatch<Action> | undefined>(undefined);

const appReducer = (state: State, action: Action): State => {
  switch (action.type) {
    case 'SET_EXAMPLE':
      return {
        ...state,
        example: action.payload,
      };
    default:
      return state;
  }
};

export const AppProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [state, dispatch] = useReducer(appReducer, initialState);

  return (
    <AppContext.Provider value={state}>
      <AppDispatchContext.Provider value={dispatch}>
        {children}
      </AppDispatchContext.Provider>
    </AppContext.Provider>
  );
};

export const useAppState = (): State => {
  const context = useContext(AppContext);
  if (context === undefined) {
    throw new Error('useAppState must be used within an AppProvider');
  }
  return context;
};

export const useAppDispatch = (): React.Dispatch<Action> => {
  const context = useContext(AppDispatchContext);
  if (context === undefined) {
    throw new Error('useAppDispatch must be used within an AppProvider');
  }
  return context;
};
```

### frontend/src/services/apiService.ts
```tsx
import axios from 'axios';

const API_BASE_URL = 'http://localhost:4000/api'; // Replace with your actual API base URL

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const fetchExampleData = async () => {
  const response = await apiClient.get('/example');
  return response.data;
};
```

### frontend/package.json
```json
{
  "name": "modern-egp-app",
  "version": "1.0.0",
  "private": true,
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  },
  "dependencies": {
    "@types/react": "^18.0.0",
    "@types/react-dom": "^18.0.0",
    "axios": "^0.21.1",
    "react": "^18.0.0",
    "react-dom": "^18.0.0",
    "react-router-dom": "^6.0.0",
    "react-scripts": "4.0.3",
    "tailwindcss": "^2.2.19",
    "typescript": "^4.1.2"
  },
  "devDependencies": {
    "@types/react-router-dom": "^5.1.7",
    "postcss": "^8.3.11",
    "autoprefixer": "^10.3.4"
  }
}
```

### frontend/tsconfig.json
```json
{
  "compilerOptions": {
    "target": "es5",
    "lib": ["dom", "dom.iterable", "esnext"],
    "allowJs": true,
    "skipLibCheck": true,
    "esModuleInterop": true,
    "allowSyntheticDefaultImports": true,
    "strict": true,
    "forceConsistentCasingInFileNames": true,
    "noFallthroughCasesInSwitch": true,
    "module": "esnext",
    "moduleResolution": "node",
    "resolveJsonModule": true,
    "isolatedModules": true,
    "noEmit": true,
    "jsx": "react-jsx"
  },
  "include": ["src"]
}
```

### frontend/tailwind.config.js
```js
module.exports = {
  purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [],
};
```

### frontend/postcss.config.js
```js
module.exports = {
  plugins: {
    tailwindcss: {},
    autoprefixer: {},
  },
};
```

### frontend/src/index.tsx
```tsx
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);
```

### frontend/src/index.css
```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

This structure should get you started on modernizing your legacy application to a React 18+ application with TypeScript, Tailwind CSS for styling, and React Router for routing. The state management is handled using the Context API and the services are set up for making API calls using Axios. Feel free to expand and modify the components, services, and context as per your application's requirements.

Sure, here's how you can structure the required files for your project:

### 1. Database Schema (SQL)

Create a file called `schema.sql`:

```sql
-- database/schema.sql

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
```

### 2. Migration Scripts

Create a directory called `migrations` and add a file `001_initial.sql`:

```sql
-- migrations/001_initial.sql

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create posts table
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Create comments table
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
```

### 3. Docker Files

Create a file called `Dockerfile`:

```Dockerfile
# Dockerfile

# Use the official PostgreSQL image from the Docker Hub
FROM postgres:13

# Add a custom script to initialize the database
COPY ./migrations /docker-entrypoint-initdb.d/
```

### 4. Docker Compose File

Create a file called `docker-compose.yml`:

```yaml