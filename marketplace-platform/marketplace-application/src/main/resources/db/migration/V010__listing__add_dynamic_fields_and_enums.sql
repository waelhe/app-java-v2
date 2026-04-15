-- ============================================================================
-- V010 - Listing Module: Dynamic Fields and Enums
-- ============================================================================

-- Add ListingType and ProviderType support
ALTER TABLE listings ADD COLUMN type VARCHAR(20) NOT NULL DEFAULT 'PRODUCT';
ALTER TABLE provider_profiles ADD COLUMN type VARCHAR(20) NOT NULL DEFAULT 'SELLER';

-- Add dynamic fields to listings (JSONB)
ALTER TABLE listings ADD COLUMN dynamic_fields JSONB DEFAULT '{}';

-- Add schema definition to categories (JSONB)
ALTER TABLE listing_categories ADD COLUMN schema_definition JSONB DEFAULT '{}';

-- Create index for dynamic fields
CREATE INDEX idx_listings_dynamic_fields ON listings USING GIN (dynamic_fields);
