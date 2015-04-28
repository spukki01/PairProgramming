package se.chalmers.eda397.pairprogramming.test;

import junit.framework.TestCase;

import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.IMapper;
import se.chalmers.eda397.pairprogramming.util.RepositoryMapper;

public class RepositoryMapperTest extends TestCase {

    private IMapper<Repository> mMapper;


    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        this.mMapper = new RepositoryMapper();
    }

    public void testMap() throws Exception {
        String jsonString = "{ id:123, private:false, description:something, name:PairProgramming }";
        JSONObject json = new JSONObject(jsonString);

        Repository repo = this.mMapper.map(json);

        assertEquals(123, repo.getId());
        assertEquals(false, repo.isPrivate());
        assertEquals("something", repo.getDescription());
        assertEquals("PairProgramming", repo.getName());
    }
}