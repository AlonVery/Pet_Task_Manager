package bd;

import infra.db.DataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestConnection {

    @Test
    void testConnection() throws SQLException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT 1");
             ResultSet rs = ps.executeQuery()) {

            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1));
        }
    }
}
