#!/bin/bash

APP_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$APP_DIR" || exit 1

# Prefer Java installed on the system, then try the runtime bundled with VS Code.
JAVA_BIN="$(command -v java 2>/dev/null || true)"
if [ -z "$JAVA_BIN" ]; then
    for candidate in "$HOME"/.vscode/extensions/redhat.java-*/jre/*/bin/java; do
        if [ -x "$candidate" ]; then
            JAVA_BIN="$candidate"
            break
        fi
    done
fi

if [ -z "$JAVA_BIN" ]; then
    echo "Java was not found. Install Java or open the project in VS Code."
    read -r -p "Press Return to close this window..."
    exit 1
fi

exec "$JAVA_BIN" -jar "$APP_DIR/GunterForge.jar"
