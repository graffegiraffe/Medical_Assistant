SET search_path TO medical_assistant;
CREATE TABLE IF NOT EXISTS flyway_schema_history_marker (
    id SERIAL PRIMARY KEY,
    applied_at TIMESTAMP DEFAULT NOW()
);
