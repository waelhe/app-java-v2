-- ============================================================================
-- V009 - Listing Module: Add PostGIS and Location
-- ============================================================================

-- Enable PostGIS extension
CREATE EXTENSION IF NOT EXISTS postgis;

-- Add location column to listings
-- GEOGRAPHY(POINT, 4326) is standard for lon/lat (WGS 84)
ALTER TABLE listings ADD COLUMN location GEOGRAPHY(POINT, 4326);

-- Add spatial index for efficient distance queries
CREATE INDEX idx_listings_location ON listings USING GIST (location);

-- Add lat/lon as separate columns for easier access (optional, but sometimes useful)
ALTER TABLE listings ADD COLUMN latitude DOUBLE PRECISION;
ALTER TABLE listings ADD COLUMN longitude DOUBLE PRECISION;

-- Function to update geography from lat/lon
CREATE OR REPLACE FUNCTION update_listing_location()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.latitude IS NOT NULL AND NEW.longitude IS NOT NULL THEN
        NEW.location = ST_SetSRID(ST_MakePoint(NEW.longitude, NEW.latitude), 4326)::geography;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to keep location in sync with lat/lon
CREATE TRIGGER trg_listings_update_location
BEFORE INSERT OR UPDATE OF latitude, longitude ON listings
FOR EACH ROW EXECUTE FUNCTION update_listing_location();
