package se.chalmers.eda397.pairprogramming.test;

import junit.framework.TestCase;

import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.util.BranchMapper;
import se.chalmers.eda397.pairprogramming.util.IMapper;


public class BranchMapperTest extends TestCase {

    private IMapper<Branch> mMapper;


    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        this.mMapper = new BranchMapper();
    }


    public void testMap() throws Exception {
        String jsonString = "{ name: 'feat/api-wrapper' }";
        JSONObject json = new JSONObject(jsonString);

        Branch branch = this.mMapper.map(json);


        assertEquals("feat/api-wrapper", branch.getName());


    }
}