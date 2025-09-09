# MCP Weather Server

Author: Kamran Manzoor

A Python-based Model Context Protocol (MCP) server that provides weather
information using the National Weather Service (NWS) API. This server implements
the FastMCP framework with HTTP streamable transport to deliver real-time weather
data and alerts.

## Features

- **Weather Forecasts**: Get detailed weather forecasts for any US location using
  latitude and longitude coordinates
- **Weather Alerts**: Retrieve active weather alerts for any US state using
  two-letter state codes
- **Streamable HTTP Transport**: Uses MCP's streamable HTTP protocol for efficient
  real-time communication
- **NWS API Integration**: Leverages the official National Weather Service API for
  accurate and up-to-date weather information

## Requirements

- Python 3.12 or higher
- Dependencies automatically managed via `pyproject.toml`

## Installation

### Using pip (recommended)

```bash
# Navigate to the python-mcp directory
cd python-mcp

# Create virtual environment
python -m venv venv

# Activate virtual environment
source venv/bin/activate  # On macOS/Linux
# or
venv\Scripts\activate     # On Windows

# Install the package in development mode
pip install -e .
```

### Manual dependency installation

```bash
pip install httpx~=0.28.0 mcp~=1.9.0 uvicorn fastapi
```

## Usage

### Running the Server

Start the MCP weather server on the default port (8123):

```bash
python weather.py
```

Or specify a custom port:

```bash
python weather.py --port 9000
```

The server will be available at `http://localhost:8123` (or your specified port).

### Available Tools

#### 1. Get Weather Forecast

Retrieves a 5-period weather forecast for a specific location.

**Parameters:**

- `latitude` (float): Latitude coordinate of the location
- `longitude` (float): Longitude coordinate of the location

**Example:**

```python
# Get forecast for Seattle, WA (47.6062, -122.3321)
await get_forecast(47.6062, -122.3321)
```

#### 2. Get Weather Alerts

Fetches active weather alerts for a US state.

**Parameters:**

- `state` (str): Two-letter US state code (e.g., "CA", "NY", "TX")

**Example:**

```python
# Get alerts for California
await get_alerts("CA")
```

## Configuration

The server can be configured through command-line arguments:

- `--port`: Port number for the server (default: 8123)

### Server Configuration Options

The FastMCP server is configured with:

- `json_response=False`: Uses SSE (Server-Sent Events) streams instead of JSON
- `stateless_http=False`: Maintains stateful connections for better performance

## API Details

### National Weather Service API

This server uses the official NWS API with:

- **Base URL**: `https://api.weather.gov`
- **User Agent**: `weather-app/1.0`
- **Accept Header**: `application/geo+json`
- **Timeout**: 30 seconds per request

### Error Handling

The server includes comprehensive error handling for:

- Network connectivity issues
- Invalid coordinates or state codes
- API rate limiting
- Malformed responses

## Development

### Project Structure

```text
python-mcp/
├── weather.py          # Main MCP server implementation
├── pyproject.toml      # Project configuration and dependencies
├── README.md           # This documentation
├── build/              # Build artifacts (auto-generated)
└── *.egg-info/         # Package metadata (auto-generated)
```

### Code Quality

This project follows Python best practices:

- Type hints for all function parameters and return values
- Comprehensive docstrings following PEP 257 conventions
- PEP 8 compliant code formatting
- Proper error handling and edge case management

### Building the Package

```bash
# Build distribution packages
python -m build

# Install in development mode
pip install -e .
```

## Integration with VS Code

This MCP server can be integrated with VS Code through the MCP protocol.
Configure your VS Code settings to connect to the running server endpoint.

## Troubleshooting

### Common Issues

1. **Port already in use**: Change the port using `--port` argument
2. **Network errors**: Check internet connectivity and firewall settings
3. **Invalid coordinates**: Ensure latitude/longitude are within US boundaries
4. **State code errors**: Use proper two-letter US state abbreviations

### Debug Mode

For debugging, you can modify the server configuration in `weather.py`:

- Set `json_response=True` for easier debugging with JSON responses
- Set `stateless_http=True` for stateless operation

## License

This project is part of the hello-world-java-service repository and follows
the same licensing terms.

## Contributing

1. Follow the Python coding conventions defined in
   `.github/instructions/python.instructions.md`
2. Ensure all functions include proper type hints and docstrings
3. Test both forecast and alert functionality before submitting changes
4. Maintain compatibility with Python 3.12+

## Related Projects

- **Parent Project**: hello-world-java-service (Spring Boot microservice)
- **MCP Protocol**: Model Context Protocol specification
- **NWS API**: National Weather Service API documentation
