/**
 * Client-Side Proctoring Monitor
 * This script runs in the student's browser and captures cheating events.
 */

const attemptId = document.getElementById('attemptId').value;
const authHeader = 'Bearer ' + localStorage.getItem('token'); // Assuming JWT is in localStorage

function sendEvent(eventType, severity, metadata = {}) {
    const payload = {
        attemptId: attemptId,
        eventType: eventType,
        severity: severity,
        metadata: metadata
    };

    // Use sendBeacon for reliable delivery even if the tab is closing, or standard fetch
    fetch('/api/proctoring/events', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': authHeader
        },
        body: JSON.stringify(payload)
    }).catch(err => console.error("Failed to send proctoring event", err));
}

// --- 1. Tab Switch / Window Focus ---
document.addEventListener('visibilitychange', () => {
    if (document.visibilityState === 'hidden') {
        sendEvent('TAB_SWITCH', 'WARNING', { state: 'hidden' });
    }
});

window.addEventListener('blur', () => {
    sendEvent('BLUR', 'WARNING', { state: 'lost_focus' });
});

// --- 2. Copy/Paste Detection ---
document.addEventListener('copy', (e) => {
    sendEvent('COPY', 'INFO');
    e.preventDefault(); // Optional: block copy entirely
});

document.addEventListener('paste', (e) => {
    sendEvent('PASTE', 'CRITICAL');
    e.preventDefault(); // Block paste
});

// --- 3. Right Click / Context Menu ---
document.addEventListener('contextmenu', (e) => {
    sendEvent('RIGHT_CLICK', 'INFO');
    e.preventDefault(); // Block right click
});

// --- 4. Fullscreen Detection ---
document.addEventListener('fullscreenchange', () => {
    if (!document.fullscreenElement) {
        sendEvent('EXIT_FULLSCREEN', 'WARNING');
    }
});

// Enforce fullscreen helper
function enterFullscreen() {
    document.documentElement.requestFullscreen().catch(err => {
        console.error("Error attempting to enable fullscreen", err);
    });
}
