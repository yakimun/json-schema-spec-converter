# JSON Schema Spec Converter

Program for converting JSON Schema specification from XML to Markdown (GFM).

## Requirements

- JDK 16+
- Maven

## Build

    mvn verify

## Usage

    java -jar ./target/spec-converter-1.0.0.jar <path>

The resulting .md file will have the same name as the specified XML file
and will be placed in the current working directory.

The first run may take some time due to the downloading of entity definitions.
Subsequent runs will be significantly faster because the downloaded entities are cached.

### Examples

#### JSON Schema

    java -jar ./target/spec-converter-1.0.0.jar ../json-schema-spec/jsonschema-core.xml

#### JSON Schema Validation

    java -jar ./target/spec-converter-1.0.0.jar ../json-schema-spec/jsonschema-validation.xml

#### Relative JSON Pointers

    java -jar ./target/spec-converter-1.0.0.jar ../json-schema-spec/relative-json-pointer.xml
