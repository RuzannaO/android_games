import React, { useEffect, useState } from 'react';
import { Browser } from '@capacitor/browser';
import { App as CapacitorApp } from '@capacitor/app';

const TARGET_URL = "https://meetqonline.com/meetqlingo/?from_app=1";
// const TARGET_URL = "https://www.your-live-website.com/";
const REDIRECT_URL = "https://meetqonline.com/meetqlingo/";

function App() {
  const [status, setStatus] = useState('checking'); // 'checking', 'ok', 'redirect', 'unavailable'

  useEffect(() => {
    // Listen for messages from the iframe
    const handleMessage = (event) => {
      if (event.data && event.data.type === 'EXIT_APP') {
        CapacitorApp.exitApp();
      }
    };

    window.addEventListener('message', handleMessage);

    // Cleanup
    return () => {
      window.removeEventListener('message', handleMessage);
    };
  }, []);

  useEffect(() => {
    const controller = new AbortController();
    const timeout = setTimeout(() => {
      console.log('Request timed out after 10 seconds.');
      controller.abort();
    }, 10000); // 10-second timeout

    fetch(TARGET_URL, {
      method: 'GET',
      redirect: 'manual',
      signal: controller.signal,
    })
      .then((response) => {
        clearTimeout(timeout);
        console.log('Fetch response:', response);
        if (response.type === 'opaqueredirect' || (response.status >= 300 && response.status < 400)) {
          setStatus('redirect');
        } else if (response.ok) {
          setStatus('ok');
        } else {
          setStatus('unavailable');
        }
      })
      .catch((err) => {
        clearTimeout(timeout);
        if (err.name === 'AbortError') {
          console.log('Fetch aborted due to 10s timeout.');
        } else {
          console.log('Fetch error:', err);
        }
        setStatus('unavailable');
      });

    return () => {
      clearTimeout(timeout);
    };
  }, []);

  const handleExit = () => {
    CapacitorApp.exitApp();
  };

  if (status === 'checking') {
    return (
      <div style={styles.container}>
        <h2 style={{ ...styles.text, fontSize: '36px', color: '#e37700' }}>
          MeetqOnline School
        </h2>
        <h2 style={styles.text}>Checking server status...</h2>
        <button onClick={handleExit} style={styles.button}>Close</button>
      </div>
    );
  }

  if (status === 'ok') {
    return (
      <iframe
        src={TARGET_URL}
        title="MeetqLingo"
        style={{ width: '100vw', height: '100vh', border: 'none' }}
      />
    );
  }

  if (status === 'redirect') {
    return (
      <div style={{ textAlign: 'center', marginTop: '30vh', color: '#b71c1c' }}>
        <h1>🔗 Redirect Detected</h1>
        <p>
          The server redirected this request to an external site.<br />
          <b>This content cannot be displayed in the app.</b>
        </p>
        <button
          style={{ ...styles.button, marginTop: 20 }}
          onClick={() => Browser.open({ url: REDIRECT_URL })}
        >
          Open in Browser
        </button>
        <button onClick={handleExit} style={{ ...styles.button, marginLeft: 10 }}>Close</button>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <h1 style={{ ...styles.text, color: '#b71c1c' }}>🚫 MeetqLingo is currently unavailable</h1>
      <p style={styles.text}>We couldn't connect to the server.</p>
      <p style={{ ...styles.text, fontSize: '2em' }}>😢</p>
      <button onClick={handleExit} style={styles.button}>Close</button>
    </div>
  );
}

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
    textAlign: 'center',
    padding: '20px',
  },
  text: {
    color: '#333',
  },
  button: {
    marginTop: '20px',
    padding: '12px 24px',
    fontSize: '1.1em',
    cursor: 'pointer',
    backgroundColor: '#f44336',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
  }
};

export default App;
